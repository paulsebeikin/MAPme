using DAL.SQLite;

namespace MAPme.Models
{
    public class AduRecord
    {
        [PrimaryKey, AutoIncrement]
        public int Id { get; set; }

        [Indexed]
        public int RecordMetaDataId { get; set; }

        [Indexed]
        public int AduUserId { get; set; }

        [NotNull]
        public string Country { get; set; }
        [NotNull]
        public string Province { get; set; }
        [NotNull]
        public string Town { get; set; }
        [NotNull]
        public string Project { get; set; }

        // locality field in ADU API
        [NotNull]
        public string Description { get; set; }

        [NotNull]
        public double Alititude { get; set; }
        [NotNull]
        public double Latitude { get; set; }
        [NotNull]
        public double Longitude { get; set; }
        [NotNull]
        public string Source { get; set; }
        [NotNull]
        public int Year { get; set; }
        [NotNull]
        public int Month { get; set; }
        [NotNull]
        public int Day { get; set; }

        // path to image on the device. Might want to investigate storing the blob in the DB (if possible)
        [NotNull]
        public string ImageUri { get; set; }

        public string Datum { get; set; }
        public string Note { get; set; }

        // represents the userdet field optional in the API
        public string Species { get; set; }

        public string Obvservers { get; set; }

        // used for the PHOWN projects (weavers)
        public int NestCount { get; set; }

        // used for the PHOWN projects (weavers)
        public string NestSite { get; set; }

        public int TaxonId { get; set; }
        public string TaxonName { get; set; }
        public string InstitutionCode { get; set; }
        public string Collection { get; set; }
        public string CatNumber { get; set; }
        public string RecordBasis { get; set; }

        // represents the optional recordStatus field in the API
        public string NatCul { get; set; }
    }
}
