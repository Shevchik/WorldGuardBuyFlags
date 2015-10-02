package wgbuyflags.utils;

import java.util.concurrent.Callable;

public class Validate {

	public static <T> T cast(Callable<T> castFunc, String errMsg) throws Validate.InvalidateException, Exception {
		try {
			return castFunc.call();
		} catch (ClassCastException e) {
			throw new Validate.InvalidateException(errMsg);
		}
	}

	public static <T> void isTrue(boolean val, String errMsg) throws Validate.InvalidateException, Exception {
		if (!val) {
			throw new Validate.InvalidateException(errMsg);
		}
	}

	public static <T> T notNull(T obj, String errMsg) throws Validate.InvalidateException, Exception {
		if (obj == null) {
			throw new Validate.InvalidateException(errMsg);
		}
		return obj;
	}

	public static class InvalidateException extends Exception {
		
		private static final long serialVersionUID = 6570789068041196738L;
	
		public InvalidateException(String message) {
			super(message);
		}
	
		@Override
		public InvalidateException fillInStackTrace() {
			return this;
		}
	
	}

}
