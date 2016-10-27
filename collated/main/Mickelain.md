# Mickelain
###### \java\seedu\address\logic\undoredomanager\FixedStack.java
``` java
 * @param Command , stack not to be saved to a file
 *
 */

@SuppressWarnings("serial")
public class FixedStack<T> extends Stack<T> {

    private int maxSize;

    public FixedStack(int size) {
        super();
        this.maxSize = size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object push(Object object) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push((T) object);
    }

}
```
