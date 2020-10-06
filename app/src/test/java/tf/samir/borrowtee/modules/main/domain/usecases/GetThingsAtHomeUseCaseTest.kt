package tf.samir.borrowtee.modules.main.domain.usecases

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import tf.samir.borrowtee.modules.main.domain.entities.AT_HOME
import tf.samir.borrowtee.modules.main.domain.entities.Thing
import tf.samir.borrowtee.modules.main.domain.repositories.ThingsRepository

class GetThingsAtHomeUseCaseTest {

    private lateinit var thingsAtHome: List<Thing>

    @Before
    fun setUp() {
        val thingsRepositoryMock = mock<ThingsRepository>{
            on { getThingsAtHome() } doReturn listOf(
                Thing(
                    id = "287584469D1068E856D9699F40255C27",
                    name = "Bluehand Protocol",
                    description = "The Bluehand Protocol: Zombies is the definitive guide against the dead " +
                            "and the living. Its 272 pages aim to not only prepare the reader for the " +
                            "impending zombie apocalypse, but also to help him survive in the difficult " +
                            "years to come.",
                    state = AT_HOME
                ),
                Thing(
                    id = "110498DD8F61517AF75D8327ECBA3FA3",
                    name = "The Outsiders – S.E. Hinton",
                    description = "This short novel is perfect for EFL learners. It has modern themes " +
                            "and typical teenage issues that people around the world have experienced. " +
                            "There are very few cultural notes in this, which means you don’t need much " +
                            "background information. The sentences are short and easy to understand. " +
                            "The vocabulary is also very easy. You should be able to read this book " +
                            "without difficulty.",
                    state = AT_HOME
                )
            )
        }

        val getThingsAtHomeUseCase = GetThingsAtHomeUseCase(
            thingsRepositoryMock
        )
        thingsAtHome = getThingsAtHomeUseCase.invoke()
    }


    @Test
    fun `There are two things`() {
        val numberOfThingsAtHome = thingsAtHome.size
        assertEquals("Number of items' wrong", 2, numberOfThingsAtHome)
    }

    @Test
    fun `It has valid id`() {
        val filteredThings = thingsAtHome.filter { thing -> thing.id.contentEquals("287584469D1068E856D9699F40255C27") }
        assertEquals("It has invalid id' wrong",
            "287584469D1068E856D9699F40255C27", filteredThings.first().id)
    }

    @Test
    fun `It has valid name`() {
        val filteredThings = thingsAtHome.filter { thing -> thing.id.contentEquals("287584469D1068E856D9699F40255C27") }
        assertEquals("It has invalid name' wrong",
            "Bluehand Protocol", filteredThings.first().name)
    }

    @Test
    fun `It has valid description`() {
        val filteredThings = thingsAtHome.filter { thing -> thing.id.contentEquals("287584469D1068E856D9699F40255C27") }
        val description = "The Bluehand Protocol: Zombies is the definitive guide against the dead " +
                "and the living. Its 272 pages aim to not only prepare the reader for the " +
                "impending zombie apocalypse, but also to help him survive in the difficult " +
                "years to come."
        assertEquals("It has invalid name' wrong",
            description, filteredThings.first().description)
    }

}