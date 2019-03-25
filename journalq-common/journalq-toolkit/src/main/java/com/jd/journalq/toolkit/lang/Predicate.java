package com.jd.journalq.toolkit.lang;

/**
 * Determines a true or false value for a given input; a pre-Java-8 version of {@code
 * java.util.function.Predicate}.
 * <p>
 * <p>The {@link Predicates} class provides common predicates and related utilities.
 * <p>
 * <p>See the Guava User Guide article on
 * <a href="https://github.com/google/guava/wiki/FunctionalExplained">the use of {@code
 * Predicate}</a>.
 * <p>
 * <h3>For Java 8+ users</h3>
 * <p>
 * <p>This interface is now a legacy type. Use {@code java.util.function.Predicate} (or the
 * appropriate primitive specialization such as {@code IntPredicate}) instead whenever possible.
 * Otherwise, at least reduce <i>explicit</i> dependencies on this type by using lambda expressions
 * or method references instead of classes, leaving your code easier to migrate in the future.
 * <p>
 * <p>To use a reference of this type (say, named {@code guavaPredicate}) in a config where {@code
 * java.util.function.Predicate} is expected, use the method reference {@code
 * guavaPredicate::apply}. For the other direction, use {@code javaUtilPredicate::test}. A future
 * version of this interface will be made to <i>extend</i> {@code java.util.function.Predicate}, so
 * that conversion will be necessary in only one direction. At that time, this interface will be
 * officially discouraged.
 *
 * @author Kevin Bourrillion
 * @since 2.0
 */
public interface Predicate<T> {
    /**
     * Returns the result of applying this predicate to {@code input} (Java 8 users, see notes in the
     * class documentation above). This method is <i>generally expected</i>, but not absolutely
     * required, to have the following properties:
     * <p>
     * <ul>
     * <li>Its execution does not cause any observable side effects.
     * <li>The computation is <i>consistent with equals</i>; that is, {@link Objects#equal
     * Objects.equal}{@code (a, b)} implies that {@code predicate.apply(a) ==
     * predicate.apply(b))}.
     * </ul>
     *
     * @throws NullPointerException if {@code input} is null and this predicate does not accept null
     *                              arguments
     */
    boolean apply(T input);

    /**
     * Indicates whether another object is equal to this predicate.
     * <p>
     * <p>Most implementations will have no reason to override the behavior of {@link Object#equals}.
     * However, an implementation may also choose to return {@code true} whenever {@code object} is a
     * {@link Predicate} that it considers <i>interchangeable</i> with this one. "Interchangeable"
     * <i>typically</i> means that {@code this.apply(t) == that.apply(t)} for all {@code t} of type
     * {@code T}). Note that a {@code false} result from this method does not imply that the
     * predicates are known <i>not</i> to be interchangeable.
     */
    @Override
    boolean equals(Object object);
}