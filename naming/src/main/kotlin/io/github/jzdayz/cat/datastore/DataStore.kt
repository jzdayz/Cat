package io.github.jzdayz.cat.datastore

import io.github.jzdayz.cat.core.Instance
import io.github.jzdayz.cat.core.Service
import java.util.concurrent.ConcurrentHashMap

interface DataStore {
    fun registerService(service: Service)
    fun registerInstance(instance: Instance)
    fun get(serviceName: String): Service?
    fun expel(serviceName: String,instanceName: String): Instance?
    fun getAll(): ConcurrentHashMap<String, Service>
}