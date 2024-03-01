package com.example.convertxmltokotlinobject.retrofit

import android.util.Log
import android.util.Xml
import okhttp3.ResponseBody
import org.xmlpull.v1.XmlPullParser
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class CustomConverterFactory: Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return XmlResponseConverter()
    }
}

class XmlResponseConverter: Converter<ResponseBody, Books> {
    override fun convert(value: ResponseBody): Books {
        val parser = Xml.newPullParser()
        parser.setInput(value.charStream())

        var eventType = parser.eventType
        var title = ""
        var author = ""
        var isbn = ""
        val books = mutableListOf<Book>()
        var currentTag: String? = null
        var isInDcIdentifierTag = false

        val xsi = "http://www.w3.org/2001/XMLSchema-instance"
        val dc = "http://purl.org/dc/elements/1.1/"
        val dcndl = "http://ndl.go.jp/dcndl/terms/"

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    currentTag = parser.name
                    val namespace = parser.getNamespace()
                    if (namespace == dc && currentTag == "identifier" && parser.getAttributeValue(xsi, "type") == "dcndl:ISBN") {
                        Log.d("XML", "Flag: true")
                        isInDcIdentifierTag = true
                    }

                    if (currentTag == "item") {
                        Log.d("XML", "item")
                        title = ""
                        author = ""
                        isbn = ""
                    }
                }

                XmlPullParser.TEXT -> {
                    when (currentTag) {
                        "title" -> title = parser.text
                        "author" -> author = parser.text
                        "identifier" -> if (isInDcIdentifierTag) {
                            Log.d("XML", "isbn: " + parser.text)
                            val txt = parser.text
                            isbn = txt
                            isInDcIdentifierTag = false
                        }
                    }
                }

                XmlPullParser.END_TAG -> {
                    if (parser.getNamespace() == "dc" && parser.name == "identifier") {
                        isInDcIdentifierTag = false
                    }

                    if (parser.name == "item") {
                        books.add(Book(title, author, isbn))
                    }
                    currentTag = null
                }
            }
            // 次のイベントに進む
            eventType = parser.next()
        }
        return Books(books)
    }
}