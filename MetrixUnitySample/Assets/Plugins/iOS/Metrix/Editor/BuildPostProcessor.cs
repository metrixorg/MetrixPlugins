using UnityEngine;
using UnityEditor;
using UnityEditor.Callbacks;
using UnityEditor.iOS.Xcode;
using UnityEditor.iOS.Xcode.Extensions;

public class BuildPostProcessor
{
    [PostProcessBuildAttribute(100)]
    public static void OnPostProcessBuild(BuildTarget target, string path)
    {
        if (target == BuildTarget.iOS)
        {
            // Get project into C#
            var projectPath = PBXProject.GetPBXProjectPath(path);
            var project = new PBXProject();
            project.ReadFromFile(projectPath);

            // Get targetGUID
            var targetGUID = project.GetUnityMainTargetGuid();
            // var targetGUID = project.GetUnityFrameworkTargetGuid();

            // Built in Frameworks
            // project.AddFrameworkToProject(targetGUID, "Foundation.framework", false);
            

            //Embed Metrix Framework
            const string metrixFramework = "Plugins/iOS/Metrix/Metrix.framework";
            string metrixGUID = project.AddFile(metrixFramework, "Frameworks/" + metrixFramework, PBXSourceTree.Sdk);
            PBXProjectExtensions.AddFileToEmbedFrameworks(project, targetGUID, metrixGUID);
            // proj.SetBuildProperty(targetGuid, "LD_RUNPATH_SEARCH_PATHS", "$(inherited) @executable_path/Frameworks");
            // proj.WriteToFile (projPath);
            //EmbedFrameworks end

            project.AddBuildProperty(targetGUID, "ALWAYS_EMBED_SWIFT_STANDARD_LIBRARIES", "YES");

            // Overwrite
            project.WriteToFile(projectPath);
        }
    }
}