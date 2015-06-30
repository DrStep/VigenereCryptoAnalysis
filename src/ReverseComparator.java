import java.util.Comparator;
import java.util.Map;

/**
 * Create comparator for sorted map
 */
public class ReverseComparator implements Comparator<String>{
    Map<String, Integer> map;

    ReverseComparator(Map<String, Integer> map) {
        this.map = map;
    }

    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b))
            return -1;
        else
            return 1;
    }
}
