library(dplyr)

#General Cleaning
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

#Unique DATE
allDates <- data5["Date"]
uniqueDate <- unique(allDates)
write.csv(uniqueDate, file="assignments-grantIee/webapp/data/RawData/dates.csv", row.names = FALSE)

#Unique VENUES
allVenues <- data5["Venue"]
uniqueVenue <- unique(allVenues)
write.csv(uniqueVenue, file="assignments-grantIee/webapp/data/RawData/venues.csv", row.names = FALSE)

#Unique LOCATIONS
allLocations <- data5["Location"]
uniqueLocation <- unique(allLocations)
write.csv(uniqueLocation, file="assignments-grantIee/webapp/data/RawData/locations.csv", row.names = FALSE)

#Unique CONDUCTORS
blankConductors <- allConductors == ""
data5$conductorName[blankConductors] <- "Not conducted" 

allConductors <- data5["conductorName"]
uniqueConductor <- unique(allConductors)
write.csv(uniqueConductor, file="assignments-grantIee/webapp/data/RawData/conductors.csv", row.names = FALSE)

#Unique PIECES
periodSong <- data5["workTitle"] == "."
eightSong <- data5["workTitle"] == "#8"
thePrayer <- data5["workTitle"] == "/ PRAYER, THE (ARR. Ross)"

data5$workTitle[periodSong] <- "WORK UNSPECIFIED"
data5$workTitle[eightSong] <- "WORK UNSPECIFIED"
data5$workTitle[thePrayer] <- "PRAYER, THE"

allPieces <- data5["workTitle"]
uniquePiece <- unique(allPieces)
write.csv(uniquePiece, file="assignments-grantIee/webapp/data/RawData/pieces.csv", row.names = FALSE)

#Unique COMPOSERS
allComposers <- data5["composerName"]
uniqueComposer <- unique(allComposers)

write.csv(uniqueComposer, file="assignments-grantIee/webapp/data/RawData/composers.csv", row.names = FALSE)

#Unique SOLOISTS
blankSoloist <- data5["soloistName"] == " "
data5$soloistName[blankSoloist] <- "No Soloist"

allSoloists <- data5["soloistName"]
uniqueSoloist <- unique(allSoloists)

write.csv(uniqueSoloist, file="assignments-grantIee/webapp/data/RawData/soloists.csv", row.names = FALSE)

#Unique INSTRUMENTS
dancer1 <- data5$soloistInstrument == "Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Choreographer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Scenic Design"
dancer2 <- data5$soloistInstrument == "Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer"
dancer3 <- data5$soloistInstrument == "Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Dancer; Choreographer; Dancer; Dancer"
dancer4 <- data5$soloistInstrument == "Dancer; Dancer; Choreographer; Choreographer"
noname <- data5$soloistInstrument == " "
others <- data5$soloistInstrument == "Other"
data5[noname,7] <- "None"
data5[others,7] <- "None"
data5[dancer1,7] <- "Dancer"
data5[dancer2,7] <- "Dancer"
data5[dancer3,7] <- "Dancer"
data5[dancer4,7] <- "Dancer"

allInstruments <- data5["soloistInstrument"]
uniqueInstrument <- unique(allInstruments)

write.csv(uniqueInstrument, file="assignments-grantIee/webapp/data/RawData/instruments.csv", row.names = FALSE)

write.csv(data5, file="assignments-grantIee/webapp/data/data2.csv")


# Link Venue Location
venLoc <- data5[,3:2]
uniqVenLoc <- unique(venLoc)
write.csv(venLoc, sep="@", row.names = FALSE, file="assignments-grantIee/webapp/data/RawData/venLoc.csv")
write.csv(uniqueVenLoc, sep="@", row.names = FALSE, file="assignments-grantIee/webapp/data/RawData/uniqVenLoc.csv")


# Link Soloist Instrument
solIns <- data5[8:7]
uniqSolIns <- unique(solIns)
write.csv(solIns, sep="@", row.names = FALSE, file="assignments-grantIee/webapp/data/RawData/solIns.csv")
write.csv(uniqSolIns, sep="@", row.names = FALSE, file="assignments-grantIee/webapp/data/RawData/uniqSolIns.csv")


# Link Composer Piece
pieCom <- data5[,6:4]
pieCom <- pieCom[,-2]
uniqPieCom <- unique(pieCom)

write.csv(uniqPieCom, sep="@", row.names = FALSE, file="assignments-grantIee/webapp/data/RawData/uniqPieCom.csv")
write.csv(pieCom, sep="@", row.names = FALSE, file="assignments-grantIee/webapp/data/RawData/pieCom.csv")
