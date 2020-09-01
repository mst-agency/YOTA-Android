package app.yota.fragment.servicemanagement

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.yota.di.Schedulers
import app.yota.domain.entity.AccountData
import app.yota.domain.repository.IAccountRepository
import app.yota.domain.repository.INotificationsRepository
import app.yota.mock
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import java.lang.Exception

class ServiceManagementViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockSchedulers: Schedulers

    @Mock
    lateinit var mockAccountRepository: IAccountRepository

    @Mock
    lateinit var mockNotificationsRepository: INotificationsRepository

    @Mock
    lateinit var mockRouter: IServiceManagementScreenRouter

    lateinit var testScheduler: TestScheduler

    @Mock
    lateinit var mockStateObserver: Observer<ServiceManagementViewModel.State>

    @Mock
    lateinit var mockAccountObserver: Observer<ServiceManagementViewModel.AccountModel>

    @Mock
    lateinit var mockNotificationObserver: Observer<MutableList<ServiceManagementViewModel.CarouselViewHolderModel>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        testScheduler = TestScheduler().also {
            Mockito.`when`(mockSchedulers.io).thenReturn(it)
            Mockito.`when`(mockSchedulers.ui).thenReturn(it)
        }
    }

    @Test
    fun `should emit error state`() {
        Mockito.`when`(mockAccountRepository.getAccountData()).thenReturn(Single.error(Exception()))
        Mockito.`when`(mockNotificationsRepository.getNotifications())
            .thenReturn(Single.error(Exception()))

        createViewModel().apply {
            testScheduler.triggerActions()
            Mockito.verify(mockStateObserver).onChanged(ServiceManagementViewModel.State.Loading)
            Mockito.verify(mockStateObserver).onChanged(ServiceManagementViewModel.State.Error)
        }
    }

    @Test
    fun `should retry load`() {
        Mockito.`when`(mockAccountRepository.getAccountData()).thenReturn(Single.error(Exception()))
        Mockito.`when`(mockNotificationsRepository.getNotifications())
            .thenReturn(Single.error(Exception()))

        createViewModel().apply {
            testScheduler.triggerActions()
            onRetryClick()
            testScheduler.triggerActions()
            Mockito.verify(mockStateObserver, times(2)).onChanged(ServiceManagementViewModel.State.Loading)
            Mockito.verify(mockStateObserver, times(2)).onChanged(ServiceManagementViewModel.State.Error)

            Mockito.verify(mockAccountRepository, times(2)).getAccountData()
            Mockito.verify(mockNotificationsRepository, times(2)).getNotifications()
        }
    }

    @Test
    fun `should emit loading state`() {
        Mockito.`when`(mockAccountRepository.getAccountData()).thenReturn(Single.never())
        Mockito.`when`(mockNotificationsRepository.getNotifications()).thenReturn(Single.never())

        createViewModel().apply {
            testScheduler.triggerActions()
            Mockito.verify(mockStateObserver).onChanged(ServiceManagementViewModel.State.Loading)
        }
    }

    @Test
    fun `should emit content state`() {
        Mockito.`when`(mockAccountRepository.getAccountData()).thenReturn(Single.just(mock<AccountData>()))
        Mockito.`when`(mockNotificationsRepository.getNotifications()).thenReturn(Single.just(listOf()))

        createViewModel().apply {
            testScheduler.triggerActions()
            Mockito.verify(mockStateObserver).onChanged(ServiceManagementViewModel.State.Loading)
            Mockito.verify(mockStateObserver).onChanged(ServiceManagementViewModel.State.Content)
        }
    }

    private fun createViewModel() =
        ServiceManagementViewModel(
            router = mockRouter,
            accountRepository = mockAccountRepository,
            notificationsRepository = mockNotificationsRepository,
            schedulers = mockSchedulers
        ).apply {
            stateLiveData.observeForever(mockStateObserver)
            accountLiveData.observeForever(mockAccountObserver)
            notificationsLiveData.observeForever(mockNotificationObserver)
        }
}