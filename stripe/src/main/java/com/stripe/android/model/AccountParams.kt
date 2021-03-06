package com.stripe.android.model

/**
 * Represents a grouping of parameters needed to create a Token for a Connect account on the server.
 */
data class AccountParams private constructor(
    private val businessType: BusinessType?,
    private val businessData: Map<String, Any>?,
    private val tosShownAndAccepted: Boolean
) : StripeParamsModel {

    /**
     * Create a string-keyed map representing this object that is ready to be sent over the network.
     *
     * @return a String-keyed map
     */
    override fun toParamMap(): Map<String, Any> {
        return mapOf("account" to
            mapOf(API_TOS_SHOWN_AND_ACCEPTED to tosShownAndAccepted)
                .plus(
                    businessType?.code?.let { code ->
                        mapOf(API_BUSINESS_TYPE to code)
                            .plus(
                                businessData?.let { mapOf(code to it) }.orEmpty()
                            )
                    }.orEmpty()
                )
        )
    }

    /**
     * See [Account creation API docs](https://stripe.com/docs/api/accounts/create#create_account-business_type)
     */
    enum class BusinessType constructor(val code: String) {
        Individual("individual"),
        Company("company")
    }

    companion object {
        internal const val API_BUSINESS_TYPE = "business_type"
        internal const val API_TOS_SHOWN_AND_ACCEPTED = "tos_shown_and_accepted"

        /**
         * Create an [AccountParams] instance for a [BusinessType.Individual] or
         * [BusinessType.Company].
         *
         * @param tosShownAndAccepted Whether the user described by the data in the token has been shown
         * the [Stripe Connected Account Agreement](https://stripe.com/docs/api/tokens/create_account#create_account_token-account-tos_shown_and_accepted).
         * When creating an account token to create a new Connect account,
         * this value must be `true`.
         * @param businessType See [BusinessType]
         * @param businessData A map of [company](https://stripe.com/docs/api/accounts/create#create_account-company)
         * or [individual](https://stripe.com/docs/api/accounts/create#create_account-individual) params.
         *
         *
         * @return [AccountParams]
         */
        @JvmStatic
        fun createAccountParams(
            tosShownAndAccepted: Boolean,
            businessType: BusinessType?,
            businessData: Map<String, Any>?
        ): AccountParams {
            return AccountParams(businessType, businessData, tosShownAndAccepted)
        }
    }
}
