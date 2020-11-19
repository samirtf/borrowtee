package ts.samir.core.base

/**
 * @author https://github.com/RohitSurwase
 */
internal interface ViewEventHandlerContract<EVENT> {
    fun handle(viewEvent: EVENT)
}