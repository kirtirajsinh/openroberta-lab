package testVisitor.mapVisitor;

import ch.qos.logback.core.joran.spi.ConsoleTarget;
import ch.qos.logback.core.net.SyslogOutputStream;
import de.fhg.iais.roberta.util.dbc.Assert;
import testVisitor.ast.A;
import testVisitor.ast.B;
import testVisitor.ast.C;
import testVisitor.ast.A;
import testVisitor.ast.B;
import testVisitor.ast.C;

import java.util.List;
import java.util.stream.Collectors;

public class AccVisitor implements AbstractVisitor<String> {
    @Override
    public String visitA(A<String> a) {
        return AbstractVisitor.super.visitAImpl(a).stream().collect(Collectors.joining(",", "TypeB [", "]"));
    }

    @Override
    public String visitB(B<String> b) {
        List<String> list = AbstractVisitor.super.visitBImpl(b);
        if (list.size() == 1 && list.get(0).equals("TypeB []")) {
            // ok
        } else {
            System.out.println("error! " + list);
        }
        return list.stream().collect(Collectors.joining(",", "TypeB [", "]"));
    }

    @Override
    public String visitC(C<String> c) {
        return AbstractVisitor.super.visitCImpl(c).stream().collect(Collectors.joining(",", "TypeB [", "]"));
    }
}
