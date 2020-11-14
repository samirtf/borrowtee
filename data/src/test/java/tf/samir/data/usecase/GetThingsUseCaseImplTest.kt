package tf.samir.data.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

import tf.samir.domain.entities.AT_HOME
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.repository.ThingRepository

class GetThingsUseCaseImplTest {

    private lateinit var thingsAtHome: Flow<List<ThingEntity>>

    @Before
    fun setUp() {
        val thingsRepositoryMock = mock<ThingRepository>{
            on { this.thingsAtHome } doReturn flow {
                emit(listOf(
                    ThingEntity(
                        id = "287584469D1068E856D9699F40255C27",
                        name = "Bluehand Protocol",
                        description = "The Bluehand Protocol: Zombies is the definitive guide against the dead " +
                                "and the living. Its 272 pages aim to not only prepare the reader for the " +
                                "impending zombie apocalypse, but also to help him survive in the difficult " +
                                "years to come.",
                        state = AT_HOME
                    ),
                    ThingEntity(
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
                ))
            }
        }

        val getThingsAtHomeUseCase = GetThingsUseCaseImpl(
            thingsRepositoryMock
        )
        thingsAtHome = getThingsAtHomeUseCase.invoke()
    }


    @Test
    fun `There are two things`() = runBlocking {
        // Test
        val numberOfThingsAtHomeFlow = thingsAtHome.first()

        // Verify
        val size = numberOfThingsAtHomeFlow.size
        assertEquals("Number of items' wrong", 2, size)
    }

    @Test
    fun `It has valid id`() = runBlocking {
        val filteredThings = thingsAtHome.first().filter { thing -> thing.id.contentEquals("287584469D1068E856D9699F40255C27") }
        assertEquals("It has invalid id' wrong",
            "287584469D1068E856D9699F40255C27", filteredThings.first().id)
    }

    @Test
    fun `It has valid name`() = runBlocking {
        val filteredThings = thingsAtHome.first().filter { thing -> thing.id.contentEquals("287584469D1068E856D9699F40255C27") }
        assertEquals("It has invalid name' wrong",
            "Bluehand Protocol", filteredThings.first().name)
    }

    @Test
    fun `It has valid description`() = runBlocking {
        val filteredThings = thingsAtHome.first().filter { thing -> thing.id.contentEquals("287584469D1068E856D9699F40255C27") }
        val description = "The Bluehand Protocol: Zombies is the definitive guide against the dead " +
                "and the living. Its 272 pages aim to not only prepare the reader for the " +
                "impending zombie apocalypse, but also to help him survive in the difficult " +
                "years to come."
        assertEquals("It has invalid name' wrong",
            description, filteredThings.first().description)
    }
}

