package io.github.jzdayz.cat.controller

import io.github.jzdayz.cat.core.Instance
import io.github.jzdayz.cat.core.R
import io.github.jzdayz.cat.datastore.DataStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ctl")
class Controller {

    @Autowired
    private lateinit var dataStore: DataStore;

    @GetMapping("/list")
    fun list(@RequestParam(defaultValue = "") serviceName: String) : Any
            = R(body =
        when(serviceName.length){
            0 -> dataStore.getAll()
            else -> dataStore.get(serviceName)
        }
    )

    @PostMapping("/register")
    fun register(@RequestBody instance: Instance) : Any
            = R(body = dataStore.registerInstance(instance))

}