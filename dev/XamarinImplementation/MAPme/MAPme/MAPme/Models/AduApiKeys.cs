using DAL.SQLite;

namespace MAPme.DAL
{
    // ported from Web.java
    public class AduApiKeys
    {
        [NotNull]
        public string Token { get; private set; }
        [NotNull]
        public string ApiKey { get; private set; }
        [NotNull]
        public string ApiKeyAlt { get; private set; }
        [NotNull]
        public string PassId { get; private set; }
        [NotNull]
        public string Username { get; private set; }
        [NotNull]
        public string UserId { get; private set; }
        [NotNull]
        public string Email { get; private set; }
        [NotNull]
        public string Project { get; private set; }
        [NotNull]
        public string Latitude { get; private set; }
        [NotNull]
        public string Longitude { get; private set; }
        [NotNull]
        public string MinimumElevation { get; private set; }
        [NotNull]
        public string MaximumElevation { get; private set; }
        [NotNull]
        public string Country { get; private set; }
        [NotNull]
        public string Province { get; private set; }
        [NotNull]
        public string NearestTown { get; private set; }
        [NotNull]
        public string Locality { get; private set; }
        [NotNull]
        public string Day { get; private set; }
        [NotNull]
        public string Month { get; private set; }
        [NotNull]
        public string Year { get; private set; }
        [NotNull]
        public string Source { get; private set; }
        [NotNull]
        public string Images { get; private set; }
        [NotNull]
        public string Note { get; private set; }
    }
}
