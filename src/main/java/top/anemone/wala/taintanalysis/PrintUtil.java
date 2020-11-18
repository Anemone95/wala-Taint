package top.anemone.wala.taintanalysis;


import top.anemone.wala.taintanalysis.domain.TaintVar;
import top.anemone.wala.taintanalysis.domain.Statement;

import java.util.LinkedList;
import java.util.List;

public class PrintUtil {
    List<Statement> path = new LinkedList<>();

    public boolean printPath(TaintVar from, TaintVar to) {
        if (from.equals(to)){
            for (Statement v:path) {
                if (v.taintVar.inst!=null){
                    System.out.println(v.taintVar.getPosition());
                } else if (v.method!=null){
                    System.out.println(v.taintVar.method);
                }
            }
            return true;
        }
        boolean found=false;
        for (TaintVar nextVar: from.propagateTaintVars){
            Statement nextTypedVar=new Statement(nextVar);
            if (!path.contains(nextTypedVar)){
                path.add(nextTypedVar);
                found|= printPath(nextVar, to);
                path.remove(nextTypedVar);
            }
        }
        return found;
    }
}
