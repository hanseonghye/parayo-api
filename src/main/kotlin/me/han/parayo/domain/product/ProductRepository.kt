package me.han.parayo.domain.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductImage, Long> {
}