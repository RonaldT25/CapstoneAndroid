package com.hfad.capstone.helper

import com.hfad.capstone.data.model.CompositionDetail
import com.hfad.capstone.data.model.ReviewResponse
import com.hfad.capstone.data.model.Transaction
import com.hfad.capstone.data.database.CompositionDetailEntity
import com.hfad.capstone.data.database.ResponseSalesEntity
import com.hfad.capstone.data.database.ReviewResponseEntity
import com.hfad.capstone.data.database.TransactionEntity
import com.hfad.capstone.data.model.ResponseSales

object DataMapper {
    fun mapResponsesToEntities(input: List<Transaction>): List<TransactionEntity> {
        val transactionList = ArrayList<TransactionEntity>()
        input.map {
            val transaction = TransactionEntity(
                Transactionid = it.id,
                TransactionstoreId = it.storeId,
                productId = it.productId,
                time = it.time,
                amount = it.amount,
                product = it.product
            )
            transactionList.add(transaction)
        }
        return transactionList
    }
    fun mapEntitiesToDomain(input: List<TransactionEntity>): List<Transaction> {
        val transactionList = ArrayList<Transaction>()
        input.map {
            val transaction = Transaction(
                id = it.Transactionid,
                storeId = it.TransactionstoreId,
                productId = it.productId,
                time = it.time,
                amount = it.amount,
                product = it.product
            )
            transactionList.add(transaction)
        }
        return transactionList
    }
    fun mapReviewResponsesToEntities(input: ReviewResponse, productId:Int): ReviewResponseEntity {
        val reviewresponse = ReviewResponseEntity(
            productId,
            input.negative,
            input.neutral,
            input.positive,
            input.image
        )
    return  reviewresponse
    }
    fun mapReviewEntitiesToDomain(input: ReviewResponseEntity): ReviewResponse {
        val reviewresponse = ReviewResponse(
            input.negativeReview,
            input.neutralReview,
            input.positiveReview,
            input.image
        )
        return  reviewresponse
    }

    fun mapAnalyzeSalesToEntities(input: ResponseSales, productId:Int): ResponseSalesEntity {
        val reviewresponse = ResponseSalesEntity(
            productId,
            input.compositions,
            input.next_6_months,
            input.start_next_6_months
        )
        return  reviewresponse
    }
    fun mapAnalyzeSalesToDomain(input: ReviewResponseEntity): ReviewResponse {
        val reviewresponse = ReviewResponse(
            input.negativeReview,
            input.neutralReview,
            input.positiveReview,
            input.image
        )
        return  reviewresponse
    }

    fun mapCompositionDetailToEntities(input: List<CompositionDetail>): List<CompositionDetailEntity> {
        val transactionList = ArrayList<CompositionDetailEntity>()
        input.map {
            val transaction = CompositionDetailEntity(
                    it.compositionId,
                    it.productId,
                    it.amount,
                    it.composition,
                    it.product
            )
            transactionList.add(transaction)
        }
        return transactionList
    }
    fun mapCompositionDetailToDomain(input: List<CompositionDetailEntity>): List<CompositionDetail> =
            input.map {
                CompositionDetail(
                        id = it.CompositionDetailId,
                        compositionId = it.composition.compositionId,
                      productId =  it.productId,
                       amount =  it.amount,
                        composition = it.composition,
                        product = it.product

                )
            }



}