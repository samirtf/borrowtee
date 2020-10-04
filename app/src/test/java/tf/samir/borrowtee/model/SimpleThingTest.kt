package tf.samir.borrowtee.model

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import tf.samir.borrowtee.modules.main.domain.entities.AT_HOME
import tf.samir.borrowtee.modules.main.domain.entities.SimpleThing
import tf.samir.borrowtee.modules.main.domain.entities.Thing

@RunWith(JUnit4::class)
class SimpleThingTest {

    private lateinit var thing: Thing

    @Before
    fun setUp() {
        thing = SimpleThing(
            id = "287584469D1068E856D9699F40255C27",
            name = "Bluehand Protocol",
            description = "The Bluehand Protocol: Zombies is the definitive guide against the dead " +
                    "and the living. Its 272 pages aim to not only prepare the reader for the " +
                    "impending zombie apocalypse, but also to help him survive in the difficult " +
                    "years to come.",
            state = AT_HOME
        )
    }

    @Test
    fun `thing has valid id`() {
        assertEquals("287584469D1068E856D9699F40255C27", thing.id)
    }

    @Test
    fun `thing has valid name`() {
        assertEquals("Bluehand Protocol", thing.name)
    }

    @Test
    fun `thing has valid description`() {
        val description = "The Bluehand Protocol: Zombies is the definitive guide against the dead " +
                "and the living. Its 272 pages aim to not only prepare the reader for the " +
                "impending zombie apocalypse, but also to help him survive in the difficult " +
                "years to come."
        assertEquals(description, thing.description)
    }

    @Test
    fun `thing's at home`() {
        assertEquals(AT_HOME, thing.state)
    }
}