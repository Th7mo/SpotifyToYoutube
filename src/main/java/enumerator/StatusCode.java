package enumerator;

public enum StatusCode {
	OK (200),
	BAD_REQUEST (400),
	UNAUTHORIZED (401);

	private final int codeNumber;

	StatusCode(int codeNumber) {
		this.codeNumber = codeNumber;
	}

	public int codeNumber() {
		return codeNumber;
	}
}
