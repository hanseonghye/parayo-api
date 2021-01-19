package me.han.parayo.domain.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductImageRepository : JpaRepository<ProductImage, Long> {
}