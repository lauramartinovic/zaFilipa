package org.unizd.rma.curko.repository

import org.unizd.rma.curko.network.RetrofitClient

class NasaRepository {
    suspend fun apodRange(apiKey: String, start: String, end: String) =
        RetrofitClient.api.getApodRange(apiKey, start, end, thumbs = true)
}
