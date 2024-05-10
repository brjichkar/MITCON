package com.android.mitcontaskmanagement.data.models.mappper

import com.android.mitcontaskmanagement.data.models.entity.UserEntity
import com.android.mitcontaskmanagement.data.models.remote.UserDTO

class UserMapper : Mapper<UserDTO, UserEntity> {
    override fun toDomainModel(network: UserDTO): UserEntity = UserEntity(
        username = network.username,
        email = network.email,
        profileImage = network.profile_img,
        hasCustomImage = network.profile_img != "",
    )

    override fun toDomainList(network: List<UserDTO>): List<UserEntity> =
        network.map { toDomainModel(it) }

    override fun toNetwork(domain: UserEntity): UserDTO = UserDTO(
        username = domain.username,
        email = domain.email,
        profile_img = domain.profileImage
    )
}
