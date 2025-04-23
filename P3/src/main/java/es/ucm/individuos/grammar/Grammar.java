package es.ucm.individuos.grammar;

import es.ucm.individuos.tree.AbstractNode;
import es.ucm.individuos.tree.ForwardNode;
import es.ucm.individuos.tree.LeftNode;
import es.ucm.individuos.tree.RightNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Grammar {
    private final List<String> grammarBNFClauses = List.of(
    "<start> ::= <code>",
    "<code> ::= <line> | <code> <line>",
    "<line> ::= <if_food> | <action>",
    "<if_food> ::= <line> <line>",
    "<action> ::= left | right | forward"
    );

    private Map<String, List<List<String>>> grammarBNFMap; // lo anterior, pero guardado en una estructura optimizada

    public Grammar() {
        grammarBNFMap = new HashMap<>();
        for (String clause: grammarBNFClauses) {
            String[] equalSep = clause.split(" ::= ");
            List<String> orSep = List.of(equalSep[1].split(" \\| "));
            grammarBNFMap.put(equalSep[0], orSep.stream().map((String el) -> List.of(el.split(" "))).toList());
        }
    }

    public List<AbstractNode> decode(List<Integer> integerList) {
        return decode(integerList, 0, "<start>");
    }

    /**
     * Funcioón recursiva para decodificar el cromosoma usando la gramática
     * @param integerList
     * @param currentPos
     * @param currentNode
     */
    public List<AbstractNode> decode(List<Integer> integerList, Integer currentPos, String currentNode) {
        List<AbstractNode> result = new LinkedList<>();
        // Object -> Action and IfFood (Action, Action)
        // Num wraps -> aplicar modulo %
        if (currentNode.equals("left")) {
            result.add(new LeftNode());
        } else if (currentNode.equals("right")) {
            result.add(new RightNode());
        } else if (currentNode.equals("forward")) {
            result.add(new ForwardNode());
        } else {
            // NO es terminal

            List<List<String>> orClauses = grammarBNFMap.get(currentNode);
            // nos quedamos una clausula (de todas las que hay juntadas por ORs)
            List<String> decodedClause = orClauses.get(integerList.get(currentPos) % orClauses.size());
            currentPos++;
            for (String op : decodedClause) {
                result.addAll(decode(integerList, currentPos, op));
            }
        }


        return result;
    }
}
