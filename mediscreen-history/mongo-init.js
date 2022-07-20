db = db.getSiblingDB('history');

db.createCollection('notes');

db.notes.insertMany(
    [
        {
            _id: ObjectId("62d10cb6bf06606cc2c282c7"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:44:06.799Z"),
            note: "Patient: TestNone Practitioner's notes/recommendations: Patient states that they are 'feeling terrific' Weight at or below recommended level  Taille",
            patientId: 1
        },
        {
            _id: ObjectId("62d10dcfbf06606cc2c282c8"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:48:47.643Z"),
            note: "Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late",
            patientId: 2
        },
        {
            _id: ObjectId("62d10ddabf06606cc2c282c9"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:48:58.461Z"),
            note: "Patient: TestBorderline Practitioner's notes/recommendations: Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic",
            patientId: 2
        },
        {
            _id: ObjectId("62d10de2bf06606cc2c282ca"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:49:06.089Z"),
            note: "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they are short term Smoker ",
            patientId: 3
        },
        {
            _id: ObjectId("62d10deabf06606cc2c282cb"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:49:14.732Z"),
            note: "Patient: TestInDanger Practitioner's notes/recommendations: Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high",
            patientId: 3
        },
        {
            _id: ObjectId("62d10df4bf06606cc2c282cc"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:49:24.483Z"),
            note: "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication",
            patientId: 4
        },
        {
            _id: ObjectId("62d10dfdbf06606cc2c282cd"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:49:33.363Z"),
            note: "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are experiencing back pain when seated for a long time",
            patientId: 4
        },
        {
            _id: ObjectId("62d10e06bf06606cc2c282ce"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:49:42.983Z"),
            note: "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that they are a short term Smoker Hemoglobin A1C above recommended level",
            patientId: 4
        },
        {
            _id: ObjectId("62d10e0fbf06606cc2c282cf"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-15T06:49:51.715Z"),
            note: "Patient: TestEarlyOnset Practitioner's notes/recommendations: Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction",
            patientId: 4
        },
        {
            _id: ObjectId("62d288455ea88c542040cb8c"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-16T09:43:33.101Z"),
            note: "microALBumine taILLE",
            patientId: 2
        },
        {
            _id: ObjectId("62d288fd5ea88c542040cb8d"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-16T09:46:37.755Z"),
            note: "ANOrmaL FuMEur VerTige",
            patientId: 3
        },
        {
            _id: ObjectId("62d28b395ea88c542040cb8e"),
            _class: "com.mediscreen.history.model.PractitionerNote",
            creationDate: new ISODate("2022-07-16T09:56:09.600Z"),
            note: "HéMoGloBine A1c, micRoAlbumiNe, taILLE, PoidS, FUmEUR, AnorMAL, ChOlesTéRol, VerTIGE,",
            patientId: 4
        }
    ]);