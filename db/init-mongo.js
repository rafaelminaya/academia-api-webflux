db.createCollection("estudiantes");
db.createCollection("cursos");
db.createCollection("matriculas");
db.createCollection("roles");
db.createCollection("users");

db.estudiantes.insertMany([
  {
    _id: ObjectId("68059e1bbb9dcaf497744321"),
    nombres: "rafael",
    apellidos: "Minaya",
    dni: "72927343",
    edad: "29",
  },
  {
    _id: ObjectId("68059e1bbb9dcaf497744322"),
    nombres: "Vanesa",
    apellidos: "Fermin",
    dni: "74937293",
    edad: "26",
  },
  {
    _id: ObjectId("68059e1bbb9dcaf497744323"),
    nombres: "Ruben",
    apellidos: "Juarez",
    dni: "49827391",
    edad: "30",
  },
]);

db.cursos.insertMany([
  {
    _id: ObjectId("68059e1bbb9dcaf497744324"),
    nombre: "Algebra",
    siglas: "ALG",
    estado: true,
  },
  {
    _id: ObjectId("68059e1bbb9dcaf497744325"),
    nombre: "Fisica",
    siglas: "FIS",
    estado: true,
  },
  {
    _id: ObjectId("68059e1bbb9dcaf497744326"),
    nombre: "Quimica",
    siglas: "QUI",
    estado: true,
  },
]);

db.matriculas.insertOne(
  {
    _id: ObjectId("68059e1bbb9dcaf497744326"),
    fechaMatricula: "2025-05-06T19:57:14.024+00:00",
    estudiante: {
      _id: ObjectId("68059e1bbb9dcaf497744322"),
      nombres: "Vanesa",
      apellidos: "Fermin",
      dni: "74937293",
      edad: "26",
    },
    cursos: [
      {
        _id: ObjectId("68059e1bbb9dcaf497744324"),
        nombre: "Algebra",
        siglas: "ALG",
        estado: true,
      },
      {
        _id: ObjectId("68059e1bbb9dcaf497744325"),
        nombre: "Fisica",
        siglas: "FIS",
        estado: true,
      },
        {
          _id: ObjectId("68059e1bbb9dcaf497744326"),
          nombre: "Quimica",
          siglas: "QUI",
          estado: true,
        },
    ],
    estado: true
  },
);

db.roles.insertMany([
    {
        _id : ObjectId("5e0181036406aa781ce6440e"),
        name : "ADMIN"
    },
    {
        _id : ObjectId("5e0181106406aa781ce6440f"),
        name : "USER"
    },
]);

db.users.insertMany([
    {
        _id : ObjectId("5e01811b6406aa781ce64410"),
        username : "mitocode",
        password : "$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.", //123
        status : true,
        roles : [
            {
                "_id" : ObjectId("5e0181036406aa781ce6440e") // ADMIN
            }
        ]
    },
    {
        _id : ObjectId("5e052cd662f50066a04e0460"),
        username : "code",
        password : "$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.", //123
        status : true,
        roles : [
            {
                "_id" : ObjectId("5e0181106406aa781ce6440f") // USER
            }
        ]
    },
]);

