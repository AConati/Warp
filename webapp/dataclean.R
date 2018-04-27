library(dplyr)

data <- read.csv("~/Downloads/ny_phil.csv")
data2 <- select(data, -c(eventType, id, Time, interval,  soloistRoles, season, programID, orchestra, movement))
duplicate <- duplicated(data2)
head(duplicate, n = 30)
data3 <- data2[!duplicate, ]
data3$workTitle[1152] == ""
noWorkTitle <- data3$workTitle == ""
data4 <- data3[!noWorkTitle, ]
data4$soloistName[data4$soloistName == ""] <- "No Soloist"
data4$soloistInstrument[data4$soloistInstrument == ""] <- "Other"
noSoloistInstrument <- data4$soloistInstrument == ""
data5 <- data4[!noSoloistInstrument, ]
summary(data5)



write.csv(data5, file="assignments-grantIee/webapp/data2.csv")



# Summary of All Unique (Use for Expected):
rapply(data5, function(x)length(table(x)))

#Unique DATE
allDates <- data5["Date"]
uniqueDate <- unique(allDates)
write.csv(uniqueDate, file="assignments-grantIee/webapp/dates.csv")

#Unique VENUES
allVenues <- data5["Venue"]
uniqueVenue <- unique(allVenues)
write.csv(uniqueVenue, file="assignments-grantIee/webapp/venues.csv")


#Unique LOCATIONS
allLocations <- data5["Location"]
uniqueLocation <- unique(allLocations)
write.csv(uniqueLocation, file="assignments-grantIee/webapp/locations.csv")

#Unique CONDUCTORS
allConductors <- data5["conductorName"]
uniqueConductor <- unique(allConductors)
write.csv(uniqueConductor, file="assignments-grantIee/webapp/conductors.csv")

#Unique PIECES
allPieces <- data5["workTitle"]
uniquePiece <- unique(allPieces)
write.csv(uniquePiece, file="assignments-grantIee/webapp/pieces.csv")

#Unique COMPOSERS
allComposers <- data5["composerName"]
uniqueComposer <- unique(allComposers)
write.csv(uniqueComposer, file="assignments-grantIee/webapp/composers.csv")

#Unique SOLOISTS
allSoloists <- data5["soloistName"]
uniqueSoloist <- unique(allSoloists)
write.csv(uniqueSoloist, file="assignments-grantIee/webapp/soloists.csv")

#Unique INSTRUMENTS
dancer1 <- data5$soloistInstrument == "Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Choreographer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Scenic Design"
dancer2 <- data5$soloistInstrument == "Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer"
dancer3 <- data5$soloistInstrument == "Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Choreographer; Dancer; Dancer"
dancer4 <- data5$soloistInstrument == "Dancer; Dancer; Choreographer; Choreographer"
data5[dancer1,7] <- "Dancer"
summary(data5)

allInstruments <- data5["soloistInstrument"]


uniqueInstrument <- unique(allInstruments)

write.csv(uniqueInstrument, file="assignments-grantIee/webapp/instruments.csv")

