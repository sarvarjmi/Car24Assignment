package com.noorheroes.car24assignment.core.database.repository

import com.noorheroes.car24assignment.core.database.dao.ComponentDao
import com.noorheroes.car24assignment.core.database.dao.ScreenDao
import com.noorheroes.car24assignment.core.database.dao.SectionDao
import com.noorheroes.car24assignment.core.database.database.SDUIDatabase
import com.noorheroes.car24assignment.core.database.entity.ScreenEntity
import com.noorheroes.car24assignment.core.database.mapper.ModelMapper
import com.noorheroes.car24assignment.core.model.json.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class ScreenRepositoryTest {

    private lateinit var repository: ScreenRepositoryImpl
    private val database: SDUIDatabase = mockk(relaxed = true)
    private val screenDao: ScreenDao = mockk(relaxed = true)
    private val sectionDao: SectionDao = mockk(relaxed = true)
    private val componentDao: ComponentDao = mockk(relaxed = true)
    private val mapper: ModelMapper = mockk(relaxed = true)
    private val json: Json = Json { ignoreUnknownKeys = true; classDiscriminator = "type" }

    @Before
    fun setup() {
        repository = ScreenRepositoryImpl(
            database, screenDao, sectionDao, componentDao, mapper, json
        )
    }

    @Test
    fun `verify saveScreenModel calls delete then insert`() = runBlocking {
        val mockModel = ScreenModel(
            metadata = MetadataModel("test_id", "Test", "1.0", "1.0", 0, 0),
            configuration = ConfigurationModel(),
            theme = ThemeModel(),
            layout = LayoutModel("LazyColumn"),
            sections = emptyList()
        )

        repository.saveScreenModel(mockModel)

        coVerify { componentDao.deleteComponentsByScreenId("test_id") }
        coVerify { sectionDao.deleteSectionsByScreenId("test_id") }
        coVerify { screenDao.insertScreen(any()) }
    }
}
