package io.github.jzdayz.cat.datastore

import io.github.jzdayz.cat.Utils
import io.github.jzdayz.cat.core.Instance
import io.github.jzdayz.cat.core.Service
import java.util.concurrent.ConcurrentHashMap

@org.springframework.stereotype.Service
class DefaultDataStore : DataStore {

    private var storage: ConcurrentHashMap<String,Service> = ConcurrentHashMap()

    override fun registerService(service: Service) = synchronized(Utils.javaClass) {
        val ser = storage[service.name]
        if (ser != null)
            ser.instances.putAll(service.instances)
        else
            storage[service.name] = service
    }

    override fun registerInstance(instance: Instance) = synchronized(Utils.javaClass) {
        val service = storage[instance.serviceName]
        if (service != null)
            service.instances[instance.name] = instance
        else
            storage[instance.serviceName] = Service(instance.serviceName, Utils.concurrentHashMap(instance.name,instance))
    }

    override fun get(serviceName: String): Service? = storage[serviceName] as Service

    override fun expel(serviceName: String, instanceName: String) =
            (storage[serviceName] as Service).instances.remove(instanceName)

    override fun getAll(): ConcurrentHashMap<String,Service> = storage
}