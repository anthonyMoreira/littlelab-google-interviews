package littlelab.google.interviews

import groovy.transform.CompileStatic
import org.apache.commons.lang.math.NumberUtils

@CompileStatic
class CompressionDecompressionService {

    private static final int INITIAL_STATE = 0
    private static final int PARSING_REPETITION = 1
    private static final int PARSING_STRING = 2

    /**
     * Decompress a compressed string respecting the google interview formalism.
     * In this algorithm we use a state machine and recursion to decompress the string
     *
     * @param input The string to decompressStateMachine
     * @return the string decompressed
     */
    String decompressStateMachine(String input) {
        StringBuilder repetition = new StringBuilder()
        StringBuilder content = new StringBuilder()
        StringBuilder finalOutput = new StringBuilder()
        int state = INITIAL_STATE
        int level = 0
        for (String character in input) {
            /*
            States description
            INITIAL_STATE : Starting state, means we are parsing char
            PARSING_REPETITION : We were at INITIAL_STATE and found a digit
            PARSING_STRING: we were at PARSING_REPETITION and now we are looking for the content to repeat.
            When finishing to parse the content we go back to INITIAL_STATE.
            */
            if (NumberUtils.isNumber(character) && state == INITIAL_STATE) {
                state = PARSING_REPETITION
            } else if (character == '[' && state == PARSING_REPETITION) {
                state = PARSING_STRING
                continue
            } else if (state == PARSING_STRING && character == '[') {
                //We are parsing the content but we found a new call inside meaning we can't stop at the first ']' to finish reading the content
                level++
            } else if (character == ']' && state == PARSING_STRING) {
                if (level <= 0) {
                    //It's the last ']' meaning we have successfully parsed the content
                    finalOutput.append(repeatContent(decompressStateMachine(content.toString()), repetition.toInteger()))
                    repetition = new StringBuilder()
                    content = new StringBuilder()
                    state = INITIAL_STATE
                }
                level--
            } else if (state == INITIAL_STATE) { //Just a char
                finalOutput.append(character)
            }
            if (state == PARSING_REPETITION) {
                repetition.append(character)
            } else if (state == PARSING_STRING) {
                content.append(character)
            }
        }
        return finalOutput
    }

    String repeatContent(String content, Integer repetition) {
        StringBuilder builder = new StringBuilder()
        for (int i = 0; i < repetition; i++) {
            builder.append(content)
        }
        builder.toString()
    }

    private class Node {
        Node parent
        List<Node> children = []
        Integer repetition
        String content

        Node(Node parent, Integer repetition, String content) {
            this.parent = parent
            this.repetition = repetition
            this.content = content
        }
    }

    /**
     * Decompress a compressed string respecting the google interview formalism.
     * In this algorithm we use a tree to deal with the recursion.
     *
     * It's an alternative to the state machine algorithm that doesn't use recursion, in case the recursion limit is hit
     * Since we store the complete tree in memory before doing any processing it doesn't scale very well
     *
     * @param input The string to decompressStateMachine
     * @return the string decompressed
     */
    String decompressWithTree(String input) {

        //1. Create the root of the tree
        Node root = new Node(null, 1, input)
        //Each repetition pattern (x[ * ]) is a node with a parent, a number of repetition and a content to repeat
        root.children = computeNodes(root, input)

        //2. To deal with the recursion, we will compute the children for each node until there is no repetition pattern
        Stack<Node> stackNode = new Stack<>()
        root.children.each { stackNode.push(it) }

        Stack<Node> stackLeaves = new Stack<>()
        while (!stackNode.isEmpty()) {
            def node = stackNode.pop()
            if (node.repetition != null) {
                node.children = computeNodes(node, node.content)
                node.children.each { stackNode.push(it) }
            } else {
                stackLeaves.push(node)
            }
        }

        //3. Now that we have a complete tree, we can traverse the tree from its leaves,
        // compute the content for each node and ultimately bubble the final result to the root
        while (!stackLeaves.isEmpty()) {
            def node = stackLeaves.pop()

            if (node.children) {
                ///!\ Order of the children must be respected to construct the string
                StringBuffer buffer = new StringBuffer()
                node.children.each {
                    buffer.append(it.content)
                }
                node.content = repeatContent(buffer.toString(), node.repetition)
            }
            if (node.parent) {
                stackLeaves.push(node.parent)
            }
        }
        return root.content
    }

    private List<Node> computeNodes(Node parent, String input) {
        List<Node> nodes = []
        int i = 0
        StringBuffer bufferSimpleChar = new StringBuffer()
        while (i < input.length()) {
            def character = input[i]
            //The number is beginning of the repetition pattern (ex: 4[a]), the end is the ']' char
            if (NumberUtils.isNumber(character)) {
                //Flush all the simple chars (ex: 'aaa4[a]') into a node
                flushBuffer(bufferSimpleChar, nodes, parent)

                //1. Find the repetition
                StringBuilder repetition = new StringBuilder()
                while (NumberUtils.isNumber(character)) {
                    repetition.append(character)
                    i++
                    character = input[i]
                }
                i++ //Skip [
                //Parsing the content now, we use levels to detect where the content ends in case of repetition
                //Ex: 4[2[a]] -> content has to be 2[a]
                int level = 1
                StringBuilder content = new StringBuilder()
                while (level > 0) {
                    character = input[i]
                    i++
                    if (character == ']') {
                        level--
                    } else if (character == '[') {
                        level++
                    }
                    if (level > 0) {
                        content.append(character)
                    }
                }
                nodes << new Node(parent, repetition.toInteger(), content.toString())
            } else {
                i++
                bufferSimpleChar.append(character)
            }
        }
        //Flushes the remaining char into a leaf
        flushBuffer(bufferSimpleChar, nodes, parent)
        return nodes
    }

    private void flushBuffer(StringBuffer bufferSimpleChar, List<Node> nodes, Node parent) {
        if (bufferSimpleChar.length() > 0) {
            nodes << new Node(parent, null, bufferSimpleChar.toString())
            bufferSimpleChar.setLength(0)
        }
    }
}
