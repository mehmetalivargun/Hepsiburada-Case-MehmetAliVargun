package com.mehmetalivargun.hepsiburadacase.util.constants

import com.mehmetalivargun.hepsiburadacase.data.model.Entity

enum class EntityType(val value : Entity) {
    APPS(Entity("Apps","software")),
    MOVIES(Entity("Movies","movie")),
    BOOKS(Entity("Books","ebook")),
    MUSIC(Entity("Music","song"));

    companion object {
        fun getEntitiyBySelected(type:String):EntityType? {

            values().find { it.value.name== type }?.let {
                return it
            }
            return null

        }
    }
}