package cz.kutner.comicsdb.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    private fun formatter() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(formatter().format(value))
    }

    override fun deserialize(decoder: Decoder): Date {
        return formatter().parse(decoder.decodeString())!!
    }
}
