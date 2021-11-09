package Model;

public enum STATUS_CODE {
	OK (200),
	BAD_REQUEST (400);

	private final int codeNumber;

	STATUS_CODE(int codeNumber) {
		this.codeNumber = codeNumber;
	}

	public int codeNumber() {
		return codeNumber;
	}
}
