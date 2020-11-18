package top.anemone.wala.taintanalysis;

import com.ibm.wala.fixpoint.BitVectorVariable;
import com.ibm.wala.util.intset.OrdinalSetMapping;
import top.anemone.wala.taintanalysis.domain.TaintVar;

import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static TaintVar getTaint(TaintVar taintVar, OrdinalSetMapping<TaintVar> taintVars, BitVectorVariable rhs) {
        return getTaint(taintVar, taintVars, rhs, new HashSet<>(), 0);
    }
    public static TaintVar getTaint(TaintVar taintVar, OrdinalSetMapping<TaintVar> taintVars, BitVectorVariable rhs, Set<TaintVar> book, int depth) {
        int idx = taintVars.getMappedIndex(taintVar);
        boolean hasTaint = idx != -1 && rhs.get(idx);
        if (hasTaint){
            return taintVar;
        }
        for (TaintVar field : taintVar.fields.values()) {
            if (!book.contains(field)){
                book.add(field);
                TaintVar taint = getTaint(field, taintVars, rhs, book, depth+1);
                book.remove(field);
                if (taint!=null) {
                    return taint;
                }
            }
        }
        return null;
    }
}
