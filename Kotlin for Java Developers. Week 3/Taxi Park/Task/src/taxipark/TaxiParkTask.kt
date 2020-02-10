package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filter { it !in tripsDrivers() }.toSet()
// or:
//        allDrivers.filter { d -> trips.none { it.driver == d } }.toSet()
// less iterations:
//        allDrivers.minus(trips.map { it.driver })
//        allDrivers - trips.map { it.driver }

private fun TaxiPark.tripsDrivers() = trips.map { it.driver }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter { p ->
            trips.filter { p in it.passengers }.count() >= minTrips
        }.toSet()

// or:
//        allPassengers.partition { p ->
//            trips.sumBy { if (p in it.passengers) 1 else 0 } >= minTrips
//        }.first.toSet()

// refactored:
//        allPassengers.filter { p ->
//            trips.count { p in it.passengers } >= minTrips
//        }.toSet()

// or:
//        trips.flatMap { it.passengers }
//                .groupBy { it }
//                .filter { it.value.size >= minTrips }
//                .map { it.key }
//                .toSet()
// refactored:
//        trips.flatMap(Trip::passengers)
//                .groupBy { it }
//             // .filter { (_, trips) -> trips.size >= minTrips } // or:
//                .filterValues { trips -> trips.size >= minTrips }
//                .keys

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers.filter { p ->
            trips/*.asSequence()*/
                    .filter { it.driver == driver }
                    .filter { p in it.passengers }
                    .count() > 1
        }.toSet()

// or:
//        allPassengers.filter { p ->
//            trips.count { it.driver == driver && p in it.passengers } > 1
//        }.toSet()

// or:
//        trips.filter { it.driver == driver }
//                .flatMap(Trip::passengers)
//                .groupBy { it }
//                .filterValues { it.size > 1 }
//                .keys


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val (dis, noDis) = trips.partition { it.discount != null }

    return allPassengers.filter { p ->
        dis.count { p in it.passengers } > noDis.count { p in it.passengers }
    }.toSet()


//    return allPassengers
//            .associateBy(
//                    { p -> p },
//                    { p -> trips.filter { p in it.passengers } })
//            .filterValues { passengerTrips ->
//                val (dis, noDis) = passengerTrips.partition { it.discount != null }
//                noDis.size > dis.size
//            }
//            .keys
}


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty()) return null
    val start = trips.groupBy { it.duration / 10 }.maxBy { it.value.size }?.key!! * 10
    val end = start + 10
    return start until end
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val totalIncome = trips.sumByDouble { it.cost }
    val twentyPercentOfTheNumberOfDrivers = (allDrivers.size * 0.2).toInt()

    val incomeForEachDriver =
            trips.groupBy { it.driver.name }
                    .map { Pair(it.key, it.value.map { t -> t.cost }.sum()) }
                    .sortedByDescending { it.second }

    val incomeTop20PercentDrivers = incomeForEachDriver.take(twentyPercentOfTheNumberOfDrivers).sumByDouble { it.second }

// refactored:
//    val incomeForEachDriver =
//            trips.groupBy(Trip::driver)
//                    .map { (_, tripsByDriver) -> tripsByDriver.sumByDouble { it.cost } }
//                    .sortedDescending()
//    val incomeTop20PercentDrivers = incomeForEachDriver.take(twentyPercentOfTheNumberOfDrivers).sum()

    return incomeTop20PercentDrivers.div(totalIncome) * 100 >= 80
}