package tf.samir.domain.entities

import androidx.annotation.IntDef

const val AT_HOME = 1
const val BORROWED = 2
const val LOST = 3

@IntDef(AT_HOME, BORROWED, LOST)
@Retention(AnnotationRetention.SOURCE)
annotation class ThingState