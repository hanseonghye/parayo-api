package me.han.parayo.domain.product

import me.han.parayo.domain.jpa.BaseEntity
import javax.persistence.*

@Entity(name = "product_image")
class ProductImage(
        var path: String,
        var productId: Long? = null
) : BaseEntity() {}