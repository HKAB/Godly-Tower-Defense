package mrmathami.utilities;

public interface Pair<A, B> {
	static <A, B> Pair<A, B> mutableOf(A a, B b) {
		return new MutableUnorderedPair<>(a, b);
	}

	static <A, B> Pair<A, B> immutableOf(A a, B b) {
		return new ImmutableUnorderedPair<>(a, b);
	}

	A getA();

	A setA(A a) throws UnsupportedOperationException;

	B getB();

	B setB(B b) throws UnsupportedOperationException;
}

