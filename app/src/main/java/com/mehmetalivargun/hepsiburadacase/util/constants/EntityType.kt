package com.mehmetalivargun.hepsiburadacase.util.constants

import com.mehmetalivargun.hepsiburadacase.data.model.Entity

enum class EntityType(val value : Entity) {
    APPS(Entity("Apps","software")),
    MOVIES(Entity("Movie","movie")),
    BOOKS(Entity("Books","ebook")),
    MUSIC(Entity("Music","song"))
}