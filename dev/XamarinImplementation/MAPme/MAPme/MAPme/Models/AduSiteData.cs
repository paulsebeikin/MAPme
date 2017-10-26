using DAL.SQLite;

namespace MAPme.DAL
{
    public class AduSiteData
    {
        // ported from constants in Web.java
        [NotNull]
        public string AuthenticationUrl { get; private set; }
        [NotNull]
        public string ValidationUrl { get; private set; }
        [NotNull]
        public string TestUrl { get; private set; }
        [NotNull]
        public string PostUrl { get; private set; }
        [NotNull]
        public string ProjectUrl { get; private set; }
    }
}
