CodeMirror.defineMode("manchestersyntax", function (config) {

    var tokenTypes = {
        "Class:": "frame-kw",
        "ObjectProperty:": "frame-kw",
        "DataProperty:": "frame-kw",
        "AnnotationProperty:": "frame-kw",
        "Individual:": "frame-kw",
        "Datatype:": "frame-kw",
        "DisjointClasses:": "gen-frame-kw",
        "DifferentIndividuals:": "gen-frame-kw",
        "EquivalentClasses:": "gen-frame-kw",
        "EquivalentProperties:": "gen-frame-kw",
        "DisjointProperties:": "gen-frame-kw",
        "SameIndividual:": "gen-frame-kw",

        "Annotations:": "section-kw",
        "SubClassOf:": "section-kw",
        "EquivalentTo:": "section-kw",
        "DisjointWith:": "section-kw",
        "DisjointUnion:": "section-kw",
        "Rule:": "section-kw",
        "SubPropertyOf:": "section-kw",
        "InverseOf:": "section-kw",
        "SubPropertyChain:": "section-kw",
        "Domain:": "section-kw",
        "Range:": "section-kw",
        "Characteristics:": "section-kw",
        "Types:": "section-kw",
        "SameAs:": "section-kw",
        "DifferentFrom:": "section-kw",
        "Facts:": "section-kw",
        "SuperClassOf:": "section-kw",
        "SuperPropertyOf:": "section-kw",
        "Individuals:": "section-kw",

        "Transitive": "characteristic-kw",
        "Functional": "characteristic-kw",
        "InverseFunctional": "characteristic-kw",
        "Symmetric": "characteristic-kw",
        "Asymmetric": "characteristic-kw",
        "Reflexive": "characteristic-kw",
        "Irreflexive": "characteristic-kw",

        "some": "quantifier-kw",
        "only": "quantifier-kw",
        "value": "quantifier-kw",
        "min": "quantifier-kw",
        "max": "quantifier-kw",
        "exactly": "quantifier-kw",
        "and": "connective-kw",
        "or": "connective-kw",
        "not": "connective-kw",
        "(": "open-par",
        ")": "close-par",
        "{": "open-par",
        "}": "close-par",
        "integer": "builtin-kw",
        "double": "builtin-kw",
        "float": "builtin-kw",
        "decimal": "builtin-kw",
        ",": "comma",
        "<": "facet",
        "<=": "facet",
        ">": "facet",
        ">=": "facet",
        "length": "facet",
        "minLength": "facet",
        "maxLength": "facet",
        "pattern": "facet",
        "langRange": "facet",
        "true" : "boolean-lit",
        "false" : "boolean-lit"
    };


//    floatingPointLiteral ::= [ '+' | '-'] ( digits ['.'digits] [exponent] | '.' digits[exponent]) ( 'f' | 'F' )
//    exponent ::= ('e' | 'E') ['+' | '-'] digits
//    decimalLiteral ::= ['+' | '-'] digits '.' digits
//    integerLiteral ::= ['+' | '-'] digits

    var tokenRegexes = {
        '<[^>]*>': "iri",
        '"(\\"|[^"])*"': "string",
        '\\^\\^[^ ]+': "literal-datatype",
        '@[^ ]+': "lang-tag",
        '(\\+|\\-)?(\\d+(\\.\\d+)?((e|E)(\\+|\\-)?\\d+)?|\\.\\d+((e|E)(\\+|\\-)?\\d+)?)(f|F)': "floating-point-lit",
        '(\\+|\\-)?\\d+\\.\\d+': "decimal-lit",
        '(\\+|\\-)?\\d+': "integer-lit"

    };


    var delimeters = {
        "(" : "delim",
        ")" : "delim",
        "{" : "delim",
        "}" : "delim",
        "[" : "delim",
        "]" : "delim",
        " " : "delim",
        "\t" : "delim",
        "\r" : "delim",
        "\n" : "delim",
        "," : "delim"
    };


    function isPrefixedWithFrameKeyword(line) {
        return isPrefixedWithTokenOfType(line, "frame-kw");
    }

    function isPrefixedWithSectionKeyword(line) {
        return isPrefixedWithTokenOfType(line, "section-kw");
    }

    function isPrefixedWithTokenOfType(line, keywordType) {
        var i = 0;
        for(i = 0; i < line.length; i++) {
            var ch = line.charAt(i);
            if(isDelimeter(ch)) {
                break
            }
        }
        return tokenTypes[line.substring(0, i)] == keywordType;
    }

    function consumeUntilTerminator(stream, state, terminatingCharacter, inStateFlag) {
        state[inStateFlag] = true;
        var buffer = stream.next();
        var nextCharIsEscaped = false;
        while(true) {
            var ch = stream.next();
            if(ch == '\\') {
                state.nextCharIsEscaped = true;
            }
            if(!state.nextCharIsEscaped && ch == terminatingCharacter) {
                buffer += ch;
                state[inStateFlag] = false;
                return buffer;
            }
            if(ch != '\\') {
                state.nextCharIsEscaped = false;
            }
            if(ch == null) {
                // Not reached the end of the state
                return buffer;
            }
        }
    }

    function isDelimeter(ch) {
        return delimeters[ch] != null;
    }

    function consumeOntologyList(stream) {
        stream.eatSpace();
        var buffer = "";
        if(stream.peek() == '[') {
            while(true) {
                var ch = stream.next();
                if(ch == null) {
                    break;
                }
                buffer += ch;
                if(ch == ']') {
                    break;
                }
            }
            return new Token();
        }
        else {
            return null;
        }
    }

    function peekDelimeter(stream, state) {
        return isDelimeter(stream.peek());
    }

    function nextToken(stream, state) {
        if(state.inString) {
            return new Token(consumeUntilTerminator(stream, state, '"', 'inString'), "string");
        }
        if(state.inAxiomPosition) {
            var ontologyList = consumeOntologyList(stream);
            if(ontologyList != null) {
                return new Token(ontologyList, "ontology-list");
            }
        }
        if(stream.peek() == '"') {
            return new Token(consumeUntilTerminator(stream, state, '"', 'inString'), "string");
        }
        if(peekDelimeter(stream, state)) {
            return new Token(stream.next(), "delim");
        }
        var tokenBuffer = "";
        while(!peekDelimeter(stream, state)) {
            if(stream.peek() == null) {
                break;
            }33
            tokenBuffer += stream.next();
        }
        var type = tokenTypes[tokenBuffer];
        console.log("Getting token types...");
        if(type == null) {
            for(var regex in tokenRegexes) {
                if (tokenRegexes.hasOwnProperty(regex)) {
                    console.log("Trying against: " + regex);
                    var regExp = new RegExp(regex);
                    var match = regExp.exec(tokenBuffer);
                    if (match != null) {
                        var firstMatch = match[0];
                        if(firstMatch == tokenBuffer) {
                            type = tokenRegexes[regex];
                            break;
                        }
                    }
                }
            }
        }
        if(type == null) {
            type = "word";
        }
        return new Token(tokenBuffer, type);
    }

    function consumeToken(token, state) {
        var type = token.type;
        state.inAxiomPosition = type == "section-kw" || type == "gen-frame-kw";
        if(token.isOpenBracket()) {
            state.nestingLevel = state.nestingLevel + 1;
        }
        else if(token.isCloseBracket()) {
            state.nestingLevel = state.nestingLevel - 1;
        }

        return type;
    }

    function Token(literal, type) {
        this.literal = literal;
        this.type = type;
        this.isOpenBracket = function() {
            return this.literal == '(' || this.literal == '{';
        };
        this.isCloseBracket = function() {
            return this.literal == ')' || this.literal == '}';
        }
    }


    return {

        startState: function() {
            return {inString: false, inIRI: false, loc: "start", nestingLevel: 0, nextCharIsEscaped: false};
        },

        token: function (stream, state) {
            var token = nextToken(stream, state);
            console.log("Token: " + token.literal + " -> " + token.type + "  Depth: " + state.nestingLevel);
            consumeToken(token, state);
            return token.type;
        }

//        indent: function(state, textAfter) {
//            if(isPrefixedWithFrameKeyword(textAfter)) {
//                return 0;
//            }
//            else if(isPrefixedWithSectionKeyword(textAfter)) {
//                return 4;
//            }
//            else {
//                return 8 + (state.nestingLevel * 4);
//            }
//        }


    }
});





CodeMirror.defineMIME("text/omn", "manchestersyntax");
