interface FirebaseService {
    @POST("api/tourist_spot/physical_disability")
    suspend fun addPhysicalDisabilityTouristSpot(@Body touristSpot: PhysicalDisabilityTouristSpotRequest)

    @POST("api/tourist_spot/visual_impairment")
    suspend fun addVisualImpairmentTouristSpot(@Body touristSpot: VisualImpairmentTouristSpotRequest)

    @POST("api/tourist_spot/hearing_impairment")
    suspend fun addHearingImpairmentTouristSpot(@Body touristSpot: HearingImpairmentTouristSpotRequest)

    @POST("api/tourist_spot/infant_family")
    suspend fun addInfantFamilyTouristSpot(@Body touristSpot: InfantFamilyTouristSpotRequest)
