package com.dwiki.nodemcu.ui.screen.monitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiki.nodemcu.data.Repository
import com.dwiki.nodemcu.data.local.model.InfoCisternModel
import com.dwiki.nodemcu.data.remote.response.MonitoringResponse
import com.dwiki.nodemcu.data.remote.response.PostResponse
import com.dwiki.nodemcu.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MonitoringViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _dataMonitoringFlow = MutableStateFlow<Resource<MonitoringResponse?>?>(null)
    val dataMonitoringFlow: StateFlow<Resource<MonitoringResponse?>?> = _dataMonitoringFlow

    private val _postHeightResponseFlow = MutableStateFlow<Resource<PostResponse?>?>(null)
    val postHeightResponseFlow: StateFlow<Resource<PostResponse?>?> = _postHeightResponseFlow

    fun getDataMonitoring() =
        viewModelScope.launch {
            _dataMonitoringFlow.value = Resource.Loading
            repository.getDataMonitoring().collect{
                _dataMonitoringFlow.value = it
            }
        }

    fun postHeightCistern(height: Int) =
        viewModelScope.launch {
            _postHeightResponseFlow.value = Resource.Loading
            repository.postHeightCistern(height).collect{
                _postHeightResponseFlow.value = it
            }
        }

    fun setInfoCistern(model: InfoCisternModel) = repository.setInfoCistern(model)
    fun setLastFill(date: String) = repository.setLastFill(date)
    fun getInfoCistern() = repository.getInfoCistern()
    fun getLastFill() = repository.getLastFill()
}