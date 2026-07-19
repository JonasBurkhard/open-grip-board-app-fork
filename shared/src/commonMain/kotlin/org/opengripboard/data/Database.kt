package org.opengripboard.data

import org.opengripboard.database.AppDatabase

class Database(
    factory: DatabaseDriverFactory
) {
    val db = AppDatabase(
        factory.createDriver()
    )
}