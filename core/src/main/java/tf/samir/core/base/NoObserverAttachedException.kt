package tf.samir.core.base

/**
 * @author https://github.com/RohitSurwase
 *
 * This is a custom NoObserverAttachedException and it does what it's name suggests.
 * Constructs a new exception with the specified detail message.
 * This is thrown, if you have not attached any observer to the LiveData.
 */
class NoObserverAttachedException(message: String) : Exception(message)