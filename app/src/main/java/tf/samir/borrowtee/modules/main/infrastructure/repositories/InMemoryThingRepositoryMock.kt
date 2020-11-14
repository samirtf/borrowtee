package tf.samir.borrowtee.modules.main.infrastructure.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tf.samir.borrowtee.modules.main.domain.entities.*
import tf.samir.domain.repositories.ThingRepository
import javax.inject.Inject

class InMemoryThingRepositoryMock @Inject constructor() :
    tf.samir.domain.repositories.ThingRepository {

    private val things = listOf(
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
            id = "CE7CAE554960CCB363B04765DD8BF8AD",
            name = "The Book of the Dead",
            description = "The Book of the Dead is important for Egyptian culture as it is " +
                    "a way of systematizing the myths and religious beliefs of the Egyptians " +
                    "concerning the afterlife, fulfilling, for the Egyptians, a function similar " +
                    "to what the sacred books of other religions fulfill today.",
            state = BORROWED
        ),
        Thing(
            id = "E138262CDFF78EC6B6DB6A3183FC28F4",
            name = "Info Magazine",
            description = "Info Exame ou simply Info was a Brazilian monthly magazine about " +
                    "technology, innovation, digital entrepreneurship and trends, published by " +
                    "Editora Abril and leader in its segment since its inception. In 1996, it " +
                    "also had an edition in Portugal.",
            state = LOST
        ),
        Thing(
            id = "73E989CBDCA6EE8FD0469719C9868209",
            name = "Charlotte’s Web – E.B. White",
            description = "This is a lovely novel that all age groups can understand. Aimed at " +
                    "native English speaking children, there are many adults who still say this " +
                    "famous book is their favorite. This is part of the national curriculum " +
                    "in many schools around the world, so it’s quite possible this book will " +
                    "also come up in conversation. You can almost guarantee that the majority " +
                    "of native English speakers have read this book at least once.",
            state = LOST
        ),
        Thing(
            id = "AD36D099C15BDBD12F3A2209F57203B7",
            name = "Mieko and the Fifth Treasure – Eleanor Coerr",
            description = "This book is not really so famous, but it is on the recommended book " +
                    "list. What’s great about “Mieko and the Fifth Treasure” is that it’s short. " +
                    "At only 77 pages long, this will be an easy read. Again this book is aimed " +
                    "at young native English speakers, so if you’re learning English, the level " +
                    "won’t be so difficult. This book will keep you interested as you’ll learn " +
                    "many interesting things about Japan and its culture.",
            state = BORROWED
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

    override val allThings: Flow<List<Thing>>
        get() = flow { emit(things) }

    override val thingsAtHome: Flow<List<Thing>>
        get() = flow { emit(things.filter { it.isAtHome() }) }
}


