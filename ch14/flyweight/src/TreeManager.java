import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeManager {

    private final List<Tree> trees = new ArrayList<>();
    private final Map<String, TreeType> treeTypes = new HashMap<>();

    public TreeType getTreeType(String name, String color, String texture) {
        return treeTypes.computeIfAbsent(name, key -> new TreeType(key, color, texture));
    }

    public void displayTrees() {
        for (Tree tree : trees) {
            tree.display();
        }
    }
}
