/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2003-2004, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |                                         **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
** $Id$
\*                                                                      */

package scala.collection.mutable;


/** This class is used internally to implement data structures that
 *  are based on resizable arrays.
 *
 *  @author  Matthias Zenger, Burak Emir
 *  @version 1.0, 03/05/2004
 */
[serializable]
abstract class ResizableArray[A] extends AnyRef with Iterable[A] {
    import java.lang.System.arraycopy;

    protected val initialSize: Int = 16;
    protected var array: Array[A] = new Array[A](initialSize);
    protected var size: Int = 0;

    /** ensure that the internal array has at n cells */
    protected def ensureSize(n: Int): Unit = {
        if (n > array.length) {
          var newsize = array.length * 2;
          while( n > newsize )
            newsize = newsize * 2;
          val newar: Array[A] = new Array(newsize);
          arraycopy(array, 0, newar, 0, size);
          array = newar;
        }
    }

    /** Swap two elements of this array.
     */
    protected def swap(a: Int, b: Int): Unit = {
        val h = array(a);
        array(a) = array(b);
        array(b) = h;
    }

    /** Move parts of the array.
     */
    protected def copy(m: Int, n: Int, len: Int) = {
        arraycopy(array, m, array, n, len);
    }

    /** Returns the length of this resizable array.
     */
    def length: Int = size;

    /** Returns a new iterator over all elements of this resizable array.
     */
    def elements: Iterator[A] = new Iterator[A] {
        var i = 0;
        def hasNext: Boolean = i < size;
        def next: A = { i = i + 1; array(i - 1) }
    }
}
