package tf.samir.infrastructure.datasource.failures

class UniqueConstraintException: Throwable()

class GenericConstraintException: Throwable()

class LocalDataSourceException(): Throwable()

class RemoteDataSourceException(): Throwable()
