package tf.samir.borrowtee.features.main.utils

import androidx.annotation.IntDef

const val STATE_UPWARD = 1
const val STATE_DOWNWARD = 2

@IntDef(STATE_UPWARD, STATE_DOWNWARD)
@Retention(AnnotationRetention.SOURCE)
annotation class ThingSortType