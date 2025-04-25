package es.ucm.individuos.grammar;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Clase que se encarga de decodificar la solución usando la gramática
 * NO SE "CONSUME" un gen si solo hay una opción en el or
 */
public class Grammar {
    private final List<String> grammarBNFClauses = List.of(
            "<start> ::= <code>",
            "<code> ::= <line> | <line> <line>",
            "<line> ::= <if_food> | <action>",
            "<if_food> ::= <code> <code>",
            "<action> ::= left | right | forward"
    );

    private Map<String, List<List<String>>> grammarBNFMap; // lo anterior, pero guardado en una estructura optimizada

    private int nWraps = 0; // numero de wraps usados (se reinicia al decodificar)
    private int currentPos = 0; // posicion del cromosoma que estamos leyendo (se reinicia al decodificar)

    private int maxWraps; // numero maximo de wraps, si se pasa devuelve NULL el decode
    public Grammar(int maxWraps) {
        this.maxWraps = maxWraps;
        grammarBNFMap = new HashMap<>();
        for (String clause: grammarBNFClauses) {
            String[] equalSep = clause.split(" ::= ");
            List<String> orSep = List.of(equalSep[1].split(" \\| "));
            grammarBNFMap.put(equalSep[0], orSep.stream().map((String el) -> List.of(el.split(" "))).toList());
        }
    }

    public List<AbstractGrammarElem> decode(List<Integer> integerList) {
        // reiniciamos
        nWraps = 0;
        currentPos = 0;
        return decode(integerList, "<start>");
    }

    /**
     * Funcioón recursiva para decodificar el cromosoma usando la gramática (USA WRAPPING)
     * @param integerList - cromosoma
     * @param currentNode - nombre (string) de la clausula de la gramatica
     */
    private List<AbstractGrammarElem> decode(List<Integer> integerList, String currentNode) {
        List<AbstractGrammarElem> result = new LinkedList<>();

        AbstractGrammarElem terminal = findTerminalById(currentNode);
        if (!isNull(terminal)) {
            // nodo terminal
            result.add(terminal.clone());
        } else {
            // control de wrapping
            if (currentPos == integerList.size()) {
                nWraps++;
                if (nWraps > maxWraps) {
                    return null;
                } else {
                    currentPos = 0;
                }
            }

            List<List<String>> orClauses = grammarBNFMap.get(currentNode);
            // nos quedamos una clausula (de todas las que hay juntadas por ORs)
            List<String> decodedClause = orClauses.get(integerList.get(currentPos) % orClauses.size());
            if (orClauses.size() > 1) {
                // NO SE CONSUME LA POSICIÓN si solo hay una opción en el or
                currentPos++;
            }

            if (isIfFood(currentNode)) {
                // si es la función if_food
                IfFoodGrammarElem elem = new IfFoodGrammarElem();
                List<AbstractGrammarElem> child1 = decode(integerList, decodedClause.get(0));
                if (child1 == null) {
                    // codigo invalido
                    return null;
                }

                List<AbstractGrammarElem> child2 = decode(integerList, decodedClause.get(1));
                if (child2 == null) {
                    // codigo invalido
                    return null;
                }
                elem.setActionsIfFood(child1);
                elem.setActionsNoFood(child2);
                result.add(elem);
            } else {
                for (String op : decodedClause) {
                    List<AbstractGrammarElem> children = decode(integerList, op);
                    if (children == null) {
                        // codigo invalido
                        return null;
                    } else {
                        result.addAll(children);
                    }
                }
            }
        }


        return result;
    }

    public AbstractGrammarElem findTerminalById(String id) {
        List<AbstractGrammarElem> terminales = List.of(
                new LeftGrammarElem(),
                new RightGrammarElem(),
                new ForwardGrammarElem()
        );

        List<AbstractGrammarElem> matched = terminales.stream().filter((t) -> t.getFuncName().equals(id)).toList();
        return matched.isEmpty() ? null : matched.get(0);
    }

    // devuelve true el id corresponde a la funcion if_food
    public boolean isIfFood(String id) {
        return (new IfFoodGrammarElem()).getFuncName().equals(id);
    }
}
