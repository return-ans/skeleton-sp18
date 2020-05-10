package hw3.hash;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* DONE: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.
        // out of int
        int N = 1000;
        ComplexOomage tmp = ComplexOomage.randomComplexOomage();
        //check whether out of bound of Integer
        int len = tmp.params.size();
        int cnt = 0;
        int curHashCode = tmp.hashCode();
        for (int i = 0; i < len; i++) {
            if (tmp.params.indexOf(i) == 0) {
                cnt++;
            }
        }
        //if out of Integer, the sign bit should be 1
        //so, use 0x7fffffff to ignore the sign bit which is 1
        //should not fail due to an IllegalArgumentException.
        for (int i = 0; i < N; i++) {
            deadlyList.add(tmp);
            //should new the new class
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /**
     * Calls tests for SimpleOomage.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
