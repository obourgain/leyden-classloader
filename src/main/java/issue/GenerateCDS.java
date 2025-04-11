package issue;

import java.util.ArrayList;

public class GenerateCDS {

    /// run with
    public static void main(String[] args) throws ClassNotFoundException {
        int count = args.length != 1 ? 100 : Integer.parseInt(args[0]);

        var classes = new ArrayList<Class<?>>(count);
        for (int i = 0; i < count; i++) {
            classes.add(Class.forName("issue.gen.Foo" + i));
        }
        System.out.println("done loading " + classes.size() + " classes");
    }
}
